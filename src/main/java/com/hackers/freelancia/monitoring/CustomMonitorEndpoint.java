package com.hackers.freelancia.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import com.sun.management.OperatingSystemMXBean;
import com.hackers.freelancia.security.JwtService;

import javax.sql.DataSource;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "custom-monitor")
public class CustomMonitorEndpoint {

    private final DataSource dataSource;
    private final JwtService jwtService;

    // ------------------- Constructeur -------------------

    public CustomMonitorEndpoint(DataSource dataSource, JwtService jwtService) {
        this.dataSource = dataSource;
        this.jwtService = jwtService;
    }

    // ------------------- Méthodes publiques -------------------
    /**
     * Point de terminaison de surveillance personnalisé qui fournit des informations sur l'état de l'application.
     *
     * @return une carte contenant les différentes métriques et informations sur l'application
     */
    @ReadOperation
    public Map<String, Object> monitor() {
        Map<String, Object> status = new HashMap<>();

        // ================= Banner Hacker =================
        status.put("banner", """
               .   ____          _            __ _ _
              /\\ / ___'_ __ _ _(_)_ __  __ _ \\ \\ \\ \\
             ( ( )\\___ | '_ | '_| | '_ \\/ _` | \\ \\ \\ \\
              \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
               '  |____| .__|_| |_|_| |_\\__, | / / / /
             =========|_|==============|___/=/_/_/_/
             """);

        // ================= Application =================
        status.put("application", System.getProperty("spring.application.name", "freelancia"));

        // ================= IP Externe =================
        status.put("externalIp", getExternalIp());

        // ================= CPU =================
        status.put("cpu", getCpuUsage());

        // ================= Mémoire JVM =================
        status.put("memory", getMemoryUsage());

        // ================= Base de données =================
        status.put("database", getDatabaseStatus());

        // ================= JWT =================
        status.put("jwtSecretSet", jwtService.getSecret() != null ? "YES ✅" : "NO ❌");

        return status;
    }

    // ------------------- Méthodes internes -------------------
    /**
     * Récupère l'adresse IP externe de l'ordinateur.
     *
     * @return l'adresse IP externe de l'ordinateur
    */
    private String getExternalIp() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface ni = interfaces.nextElement();
                if (!ni.isUp() || ni.isLoopback())
                    continue;

                Enumeration<InetAddress> addresses = ni.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (!addr.isLoopbackAddress() && addr instanceof java.net.Inet4Address) {
                        return addr.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            return "Unavailable";
        }
        return "Unavailable";
    }

    /**
     * Récupère l'utilisation actuelle du CPU.
     *
     * @return une chaîne représentant l'utilisation du CPU en pourcentage
     */
    private String getCpuUsage() {
        OperatingSystemMXBean osBean =
                (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double cpu = osBean.getCpuLoad();
        if (cpu < 0) cpu = osBean.getSystemLoadAverage() / osBean.getAvailableProcessors();
        if (cpu < 0) cpu = 0;
        return String.format("%.2f %%", cpu * 100);
    }

    /**
     * Récupère l'utilisation actuelle de la mémoire JVM.
     *
     * @return une chaîne représentant l'utilisation de la mémoire JVM en MB
     */
    private String getMemoryUsage() {
        long used = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() / (1024 * 1024);
        long max = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getMax() / (1024 * 1024);
        return used + " MB / " + max + " MB";
    }

    /**
     * Vérifie la connexion à la base de données et récupère le nom de la base de données.
     *
     * @return une chaîne indiquant si la base de données est connectée ou non, et le nom de la base de données si connecté
     */
    private String getDatabaseStatus() {
        try (var conn = dataSource.getConnection()) {
            String dbName = conn.getCatalog();
            return conn.isValid(2) ? "CONNECTED ✅ (" + dbName + ")" : "DISCONNECTED ❌";
        } catch (Exception e) {
            return "DISCONNECTED ❌";
        }
    }
}
