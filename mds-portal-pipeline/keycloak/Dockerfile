FROM quay.io/keycloak/keycloak:21.1.1 as builder

# Configure a database vendor
ENV KC_DB=postgres

WORKDIR /opt/keycloak
# for demonstration purposes only, please make sure to use proper certificates in production instead
RUN keytool -genkeypair -storepass password -storetype PKCS12 -keyalg RSA -keysize 2048 -dname "CN=server" -alias server -ext "SAN:c=DNS:localhost,IP:127.0.0.1" -keystore conf/server.keystore
RUN /opt/keycloak/bin/kc.sh build --db postgres

FROM quay.io/keycloak/keycloak:21.0.1
COPY --from=builder /opt/keycloak/ /opt/keycloak/

# change these values to point to a running postgres instance
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--http-relative-path /auth", "--import-realm"]