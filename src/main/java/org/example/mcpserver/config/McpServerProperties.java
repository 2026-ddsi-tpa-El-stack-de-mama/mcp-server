package org.example.mcpserver.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app")
public class McpServerProperties {

    @NotNull
    private Services services = new Services();

    @NotNull
    private Http http = new Http();
    @NotNull
    private Security security = new Security();

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Http getHttp() {
        return http;
    }

    public void setHttp(Http http) {
        this.http = http;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public static class Services {
        @NotBlank
        private String logisticaBaseUrl;

        @NotBlank
        private String donacionesBaseUrl;

        @NotBlank
        private String donadoresEntidadesBaseUrl;

        @NotBlank
        private String incentivosBaseUrl;

        public String getLogisticaBaseUrl() {
            return logisticaBaseUrl;
        }

        public void setLogisticaBaseUrl(String logisticaBaseUrl) {
            this.logisticaBaseUrl = logisticaBaseUrl;
        }

        public String getDonacionesBaseUrl() {
            return donacionesBaseUrl;
        }

        public void setDonacionesBaseUrl(String donacionesBaseUrl) {
            this.donacionesBaseUrl = donacionesBaseUrl;
        }

        public String getIncentivosBaseUrl() {
            return incentivosBaseUrl;
        }

        public void setIncentivosBaseUrl(String incentivosBaseUrl) {
            this.incentivosBaseUrl = incentivosBaseUrl;
        }

        public String getDonadoresEntidadesBaseUrl() {
            return donadoresEntidadesBaseUrl;
        }

        public void setDonadoresEntidadesBaseUrl(String donadoresEntidadesBaseUrl) {
            this.donadoresEntidadesBaseUrl = donadoresEntidadesBaseUrl;
        }
    }

    public static class Http {
        @NotNull
        private Duration connectTimeout = Duration.ofSeconds(10);

        @NotNull
        private Duration readTimeout = Duration.ofSeconds(60);

        public Duration getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(Duration connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public Duration getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(Duration readTimeout) {
            this.readTimeout = readTimeout;
        }
    }

    public static class Security {
        @NotBlank
        private String bearerToken;

        public String getBearerToken() {
            return bearerToken;
        }

        public void setBearerToken(String bearerToken) {
            this.bearerToken = bearerToken;
        }
    }
}
