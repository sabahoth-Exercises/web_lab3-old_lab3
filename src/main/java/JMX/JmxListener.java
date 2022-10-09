package JMX;

import javax.management.MBeanServer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.management.ManagementFactory;
import javax.management.ObjectName;

public class JmxListener implements ServletContextListener {
    public static final String PB = "JMX:type=mbean,name=Percentage";
    public static final String RB = "JMX:type=mbean,name=Repeat";

    @Override
    public void contextInitialized(final ServletContextEvent servletContextEvent) {
        try {
            final MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName PBName = new ObjectName(PB);
            ObjectName RBName = new ObjectName(RB);
            mBeanServer.registerMBean(Repeat.getInstance(), RBName);
            mBeanServer.registerMBean(Percentage.getInstance(), PBName);

            JmxNotificationListener jmxNotificationListener = new JmxNotificationListener();
            mBeanServer.addNotificationListener(PBName, jmxNotificationListener, null, null);
            mBeanServer.addNotificationListener(RBName, jmxNotificationListener, null, null);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent servletContextEvent) {
        try {
            MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
            ObjectName PBName = new ObjectName(PB);
            ObjectName RBName = new ObjectName(RB);

            mBeanServer.unregisterMBean(PBName);
            mBeanServer.unregisterMBean(RBName);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
