package org.taskspfe.pfe.service.role;


import org.taskspfe.pfe.model.role.Role;

public interface RoleService {

    public Role fetchRoleByName(final String roleName);
}
