package util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
	private static StandardServiceRegistry registry;
//	宣告為final，只有這個類別內部可以生成一個物件
	private static final SessionFactory sessionFactory = createSessionFactory();
	
//	透過封裝(public)公開的getter，可以取得sessionFactory的物件
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static SessionFactory createSessionFactory() {
		try {

			registry = new StandardServiceRegistryBuilder()
					.configure()
					.build();

			SessionFactory sessionFactory = new MetadataSources(registry)
					.buildMetadata()
					.buildSessionFactory();

			return sessionFactory;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}

	}

	public static void shutdown() {
		if (registry != null)
			StandardServiceRegistryBuilder.destroy(registry);
	}
	
}
