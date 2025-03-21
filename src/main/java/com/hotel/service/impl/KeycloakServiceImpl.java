package com.hotel.service.impl;

import com.hotel.config.KeycloakProperties;
import com.hotel.dto.UserDTO;
import com.hotel.service.KeycloakService;
import com.hotel.service.helper.KeycloakRoleManager;
import com.hotel.service.helper.KeycloakUserManager;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Slf4j
@Service
public class KeycloakServiceImpl implements KeycloakService {

    private final KeycloakUserManager userManager;
    private final KeycloakRoleManager roleManager;

    public KeycloakServiceImpl(KeycloakUserManager userManager, KeycloakRoleManager roleManager) {
        this.userManager = userManager;
        this.roleManager = roleManager;
    }

    @Override
    @Transactional
    public void userCreate(UserDTO dto) {
        UserRepresentation keycloakUser = userManager.getUserRepresentation(dto);
        try (Keycloak keycloak = userManager.getKeycloakInstance()) {
            RealmResource realmResource = keycloak.realm(getKeycloakProperties().getRealm());
            Response response = userManager.createUser(realmResource, keycloakUser);
            roleManager.updateClientRoles(keycloak.realm(getKeycloakProperties().getRealm()), getCreatedId(response), dto.getRole());
        }
    }

    @Override
    public void userUpdate(UserDTO dto) {
        try (Keycloak keycloak = userManager.getKeycloakInstance()) {
            RealmResource realm = keycloak.realm(getKeycloakProperties().getRealm());
            UserRepresentation user = userManager.findUserByEmail(realm.users(), dto.getEmail());
            userManager.updateUserFields(user, dto);
            roleManager.updateClientRoles(realm, user.getId(), dto.getRole());
            userManager.updatePasswordIfNeeded(realm.users(), user.getId(), dto.getPassword());
            realm.users().get(user.getId()).update(user);
        }
    }


    private KeycloakProperties getKeycloakProperties() {
        return userManager.getKeycloakProperties();
    }
}
