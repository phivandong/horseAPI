//package com.pvdong.lesson3.hihernate;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.*;
//
//public class HibernateUtilTest {
//    private static SessionFactory sessionFactory;
//    private Session session;
//
//    @BeforeAll
//    public static void setup() {
//        sessionFactory = HibernateUtil.getSessionFactory();
//        System.out.println("SessionFactory created");
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        if (sessionFactory != null) sessionFactory.close();
//        System.out.println("SessionFactory destroyed");
//    }
//
//    @Test
//    public void testCreate() {
//    }
//
//    @Test
//    public void testUpdate() {
//    }
//
//    @Test
//    public void testGet() {
//    }
//
//    @Test
//    public void testList() {
//    }
//
//    @Test
//    public void testDelete() {
//    }
//
//    @BeforeEach
//    public void openSession() {
//        session = sessionFactory.openSession();
//        System.out.println("Session created");
//    }
//
//    @AfterEach
//    public void closeSession() {
//        if (session != null) session.close();
//        System.out.println("Session closed\n");
//    }
//}
