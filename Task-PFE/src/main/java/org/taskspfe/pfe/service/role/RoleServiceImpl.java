package org.taskspfe.pfe.service.role;


import org.springframework.stereotype.Service;
import org.taskspfe.pfe.exceptions.ResourceNotFoundException;
import org.taskspfe.pfe.model.role.Role;
import org.taskspfe.pfe.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role fetchRoleByName(String roleName) {
        return roleRepository.fetchRoleByName(roleName).orElseThrow(
                ()-> new ResourceNotFoundException("The role with name : %s could not be found.".formatted(roleName))
        );
    }
}
