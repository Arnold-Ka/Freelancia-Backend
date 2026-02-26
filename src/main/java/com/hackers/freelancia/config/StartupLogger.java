package com.hackers.freelancia.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Arrays;

@Component
public class StartupLogger implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment environment;
    private final DataSource dataSource;

    public StartupLogger(Environment environment, DataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    /**
     * Affiche une bannière personnalisée et des informations sur l'application lorsque l'application est prête.
     * Les informations affichées incluent le nom de l'application, la version, les profils actifs, les URLs d'accès, l'état de la base de données, l'utilisation de la mémoire et du CPU.
     * @param event l'événement indiquant que l'application est prête
     * @throws Exception en cas d'erreur lors de l'exécution de la méthode
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        printBanner();

        String appName = environment.getProperty("spring.application.name", "Freelancia");
        String port = environment.getProperty("server.port", "8080");
        String contextPath = environment.getProperty("server.servlet.context-path", "");
        String version = environment.getProperty("application.version", "1.0.0");
        String profiles = Arrays.toString(environment.getActiveProfiles());

        String localUrl = "http://localhost:" + port + contextPath;
        String externalUrl = getExternalUrl(port, contextPath);

        String dbStatus = checkDatabase();
        String memory = getMemoryUsage();
        String cpu = getCpuUsage();

        System.out.println("============================================================");
        System.out.println("🚀  Application : " + appName + " v" + version);
        System.out.println("🌱  Profile     : " + profiles);
        System.out.println("------------------------------------------------------------");
        System.out.println("🏠  Local URL   : " + localUrl);
        System.out.println("🌍  VPS URL     : " + externalUrl);
        System.out.println("📚  Swagger     : " + localUrl + "/swagger-ui.html");
        System.out.println("------------------------------------------------------------");
        System.out.println("🐘  Database    : " + dbStatus);
        System.out.println("🧠  JVM Memory  : " + memory);
        System.out.println("⚙️   CPU Usage   : " + cpu);
        System.out.println("============================================================\n");
    }

    /**
     * Affiche une bannière personnalisée.
     */
    private void printBanner() {
        System.out.println("""
                ███████╗██████╗ ███████╗███████╗██╗      █████╗ ███╗   ██╗ ██████╗ ██╗ █████╗
                ██╔════╝██╔══██╗██╔════╝██╔════╝██║     ██╔══██╗████╗  ██║██╔════╝ ██║██╔══██╗
                █████╗  ██████╔╝█████╗  █████╗  ██║     ███████║██╔██╗ ██║██║      ██║███████║
                ██╔══╝  ██╔══██╗██╔══╝  ██╔══╝  ██║     ██╔══██║██║╚██╗██║██║      ██║██╔══██║
                ██║     ██║  ██║███████╗███████╗███████╗██║  ██║██║ ╚████║╚██████╔╝██║██║  ██║
                ╚═╝     ╚═╝  ╚═╝╚══════╝╚══════╝╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝╚═╝  ╚═╝
                [ FREELANCIA - HACKER MONITOR MODE ACTIVATED ]
                """);
    }

    /**
     * Récupère l'URL externe de l'ordinateur.
     *
     * @param port        le port utilisé par l'application
     * @param contextPath le chemin de contexte utilisé par l'application
     * @return l'URL externe de l'ordinateur
     */
    public String getExternalUrl(String port, String contextPath) {
        try {
            var interfaces = java.net.NetworkInterface.getNetworkInterfaces();

            while (interfaces.hasMoreElements()) {
                var networkInterface = interfaces.nextElement();

                if (!networkInterface.isUp() || networkInterface.isLoopback()) {
                    continue;
                }

                var addresses = networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    var address = addresses.nextElement();

                    if (!address.isLoopbackAddress()
                            && address instanceof java.net.Inet4Address) {

                        return "http://" + address.getHostAddress() + ":" + port + contextPath;
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return "Unavailable";
    }

    /**
     * Vérifie la connexion à la base de données et récupère le nom de la base de données.
     *
     * @return une chaîne indiquant si la base de données est connectée ou non, et le nom de la base de données si connecté
     */
    private String checkDatabase() {
        try (Connection connection = dataSource.getConnection()) {

            if (!connection.isValid(2)) {
                return "DISCONNECTED ❌";
            }

            DatabaseMetaData metaData = connection.getMetaData();
            String dbName = metaData.getDatabaseProductName();
            String dbUrl = connection.getCatalog();

            return dbName + " (" + dbUrl + ") CONNECTED ✅";

        } catch (Exception e) {
            return "DISCONNECTED ❌";
        }
    }

    /**
     * Récupère l'utilisation actuelle de la mémoire JVM.
     *
     * @return une chaîne représentant l'utilisation de la mémoire JVM en MB
     */
    private String getMemoryUsage() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        long used = memoryMXBean.getHeapMemoryUsage().getUsed() / (1024 * 1024);
        long max = memoryMXBean.getHeapMemoryUsage().getMax() / (1024 * 1024);
        return used + " MB / " + max + " MB";
    }

    /**
     * Récupère l'utilisation actuelle du CPU.
     *
     * @return une chaîne représentant l'utilisation du CPU en pourcentage
     */
    private String getCpuUsage() {
        com.sun.management.OperatingSystemMXBean osBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
                .getOperatingSystemMXBean();

        double cpuLoad = osBean.getCpuLoad();
        if (cpuLoad < 0) {
            cpuLoad = osBean.getSystemLoadAverage() / osBean.getAvailableProcessors();
            if (cpuLoad < 0)
                cpuLoad = 0;
        }

        return String.format("%.2f %%", cpuLoad * 100);
    }

}
