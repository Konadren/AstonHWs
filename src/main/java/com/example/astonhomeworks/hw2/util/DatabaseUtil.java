package com.example.astonhomeworks.hw2.util;

import com.example.astonhomeworks.hw2.models.Movie;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseUtil {

    // Интерфейс для конфигурации
    final static StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // пускай ищет hibernate.cfg.xml (с hibernate.properties увы не работает)
            .build();

    // так мы точно уверены, что sessionFactory создастся один раз в своей жизни
    private static final SessionFactory sessionFactory;

    static {
        try {
            // создаём Session Factory на основе конфигурации
            sessionFactory = new MetadataSources(registry).buildMetadata()
                    .buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    // при вызове то ли получаем сессию, то ли открываем
    public static Session getSession()
            throws HibernateException {
        Session session;
        try {
            session = sessionFactory.getCurrentSession();
        } catch (org.hibernate.HibernateException he) {
            session = sessionFactory.openSession();
        }
        return session;
    }


}
