package io.lypsis.commons.properties.utils;

public class JwtProperties {

    private String jwtSecret;
    private int jwtLifetime;
    private int jwtRenewal;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public int getJwtLifetime() {
        return jwtLifetime;
    }

    public void setJwtLifetime(int jwtLifetime) {
        this.jwtLifetime = jwtLifetime;
    }

    public int getJwtRenewal() {
        return jwtRenewal;
    }

    public void setJwtRenewal(int jwtRenewal) {
        this.jwtRenewal = jwtRenewal;
    }
}
