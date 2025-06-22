package com.utn.ProgIII.audit;

import com.utn.ProgIII.repository.UserRepository;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.exception.AuditException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuditRevisionListener implements RevisionListener {

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        AuditRevisionListener.userRepository = userRepository;
    }

    /**
     * Método que se dispara cuando se hace un cambio a cualquier entidad
     * @param revisionEntity Objeto con los cambios realizados
     */
    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevisionEntity = (AuditRevisionEntity) revisionEntity;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                String username = auth.getName();
                userRepository.findByCredential_Username(username).ifPresent(user -> {
                    auditRevisionEntity.setIdUser(user.getIdUser());
                });
            }
        } catch (Exception e) {
            throw new AuditException("Error auditando: " + e.getMessage());
        }
    }
}