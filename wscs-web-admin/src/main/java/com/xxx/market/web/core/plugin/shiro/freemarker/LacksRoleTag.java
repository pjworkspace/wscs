package com.xxx.market.web.core.plugin.shiro.freemarker;

/**
 * <p>Equivalent to {@link org.apache.shiro.web.tags.LacksRoleTag}</p>
 */
public class LacksRoleTag extends RoleTag {
  protected boolean showTagBody(String roleName) {
    boolean hasRole = getSubject() != null && getSubject().hasRole(roleName);
    return !hasRole;
  }
}
