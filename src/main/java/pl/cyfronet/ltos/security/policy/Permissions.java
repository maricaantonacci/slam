package pl.cyfronet.ltos.security.policy;

import java.util.Collection;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class Permissions {

	@Resource(name = "userPermissions")
	private Collection<Activity> userPermissions;

	public SecurityPolicy securityPolicy(Identity identity, OwnedResource targetObject, Activity activity) {
		RoleBasedSecurityPolicy<Object> roleBasedPolicy = new RoleBasedSecurityPolicy<Object>();
		roleBasedPolicy.setIdentity(identity);
		roleBasedPolicy.setUserPermissions(userPermissions);
		roleBasedPolicy.setTargetObject(targetObject);
		roleBasedPolicy.setActivity(activity);
		
		OwnerBasedSecurityPolicy ownerBasedPolicy = new OwnerBasedSecurityPolicy();
		ownerBasedPolicy.setIdentity(identity);
		ownerBasedPolicy.setTargetObject(targetObject);
		ownerBasedPolicy.setActivity(activity);
		
		return () -> { return roleBasedPolicy.evaluate() && ownerBasedPolicy.evaluate(); };
	}

}