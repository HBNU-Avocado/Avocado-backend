package io.wisoft.capstonedesign.domain.appointment.persistence;

import io.wisoft.capstonedesign.global.BaseEntity;
import io.wisoft.capstonedesign.domain.hospital.persistence.Hospital;
import io.wisoft.capstonedesign.domain.member.persistence.Member;
import io.wisoft.capstonedesign.global.enumeration.status.AppointmentStatus;
import io.wisoft.capstonedesign.global.enumeration.HospitalDept;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverrides({
        @AttributeOverride(name = "createAt", column = @Column(name = "appt_create_at", nullable = false)),
        @AttributeOverride(name = "updateAt", column = @Column(name = "appt_update_at"))
})
public class Appointment extends BaseEntity {

    @Id
    @GeneratedValue()
    @Column(name = "appointment_id")
    private Long id;

    @Column(name = "appt_dept", nullable = false)
    @Enumerated(EnumType.STRING)
    private HospitalDept dept;

    //pg 사용시 @Lob 지우고, @Column(nullable = false, columnDefinition="TEXT")로 바꾸기
    @Lob
    @Column(name = "appt_comment", nullable = false)
    private String comment;

    @Column(name = "appt_status")
    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @Column(name = "appt_name", nullable = false)
    private String appointName;

    @Column(name = "appt_phonenumber", nullable = false)
    private String appointPhonenumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hosp_id")
    private Hospital hospital;

    /* 연관관계 편의메서드 */
    public void setHospital(final Hospital hospital) {
        //comment: 기존 관계 제거
        if (this.hospital != null) {
            this.hospital.getAppointmentList().remove(this);
        }

        this.hospital = hospital;

        //comment: 무한루프 방지
        if (!hospital.getAppointmentList().contains(this)) {
            hospital.getAppointmentList().add(this);
        }
    }

    public void setMember(final Member member) {
        //comment: 기존 관계 제거
        if (this.member != null) {
            this.member.getAppointmentList().remove(this);
        }

        this.member = member;

        //comment: 무한루프에 빠지지 않도록 체크
        if (!member.getAppointmentList().contains(this)) {
            member.getAppointmentList().add(this);
        }
    }

    /* 정적 생성 메서드 */
    @Builder
    public static Appointment createAppointment(
            final Member member,
            final Hospital hospital,
            final HospitalDept dept,
            final String comment,
            final String appointName,
            final String appointPhonenumber) {

        Appointment appointment = new Appointment();
        appointment.setMember(member);
        appointment.setHospital(hospital);
        appointment.dept = dept;
        appointment.comment = comment;
        appointment.appointName = appointName;
        appointment.appointPhonenumber = appointPhonenumber;

        appointment.createEntity();
        appointment.status = AppointmentStatus.COMPLETE;

        return appointment;
    }

    /**
     * 예약 취소
     */
    public void cancel() {

        if (this.status == AppointmentStatus.CANCEL) {
            throw new IllegalStateException("이미 취소된 예약입니다.");
        }

        this.status = AppointmentStatus.CANCEL;
    }

    /**
     * 예약 수정
     */
    public void update(
            final HospitalDept dept,
            final String comment,
            final String appointName,
            final String appointPhonenumber) {
        this.dept = dept;
        this.comment = comment;
        this.appointName = appointName;
        this.appointPhonenumber = appointPhonenumber;

        this.updateEntity();
    }
}