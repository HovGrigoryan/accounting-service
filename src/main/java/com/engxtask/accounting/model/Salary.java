//package com.engxtask.accounting.model;
//
//import com.engxtask.accounting.enums.CurrencyType;
//import com.engxtask.accounting.enums.Month;
//import lombok.Data;
//
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
//
//@Entity
//@Data
//public class Salary {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @Enumerated(EnumType.STRING)
//    private CurrencyType currencyType;
//    @Enumerated(EnumType.STRING)
//    private Month month;
//    private Integer salaryAmount;
//    @OneToOne
//    @JoinColumn(name = "employee_id", referencedColumnName = "id")
//    private Employee employee;
//}
