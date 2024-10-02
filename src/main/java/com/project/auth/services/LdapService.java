package com.project.auth.services;

import java.util.List;
import java.util.jar.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;

import javax.naming.Name;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.Filter;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;

import com.project.auth.models.User;

@Service
public class LdapService {

    @Autowired
    private LdapTemplate ldapTemplate;

    @Autowired
    private LdapContextSource contextSource;

    public void createUser(String uid, String cn, String sn, String password) {
        Name dn = LdapNameBuilder.newInstance()
                .add("ou", "bolu")
                .add("uid", uid)
                .build();

        DirContextAdapter context = new DirContextAdapter(dn);
        context.setAttributeValues("objectClass", new String[] { "inetOrgPerson", "organizationalPerson", "person", "top" });
        context.setAttributeValue("cn", cn);
        context.setAttributeValue("sn", sn);
        context.setAttributeValue("uid", uid);
        context.setAttributeValue("userPassword", password);
        ldapTemplate.bind(context);
    }

    public boolean checkPass(String username, String userpassword){
        AndFilter filter = new AndFilter();
        filter.and(
            new EqualsFilter("objectclass", "person")
        ).and(
            new EqualsFilter("uid", username)
        );
        return ldapTemplate.authenticate("ou=bolu", filter.toString(), userpassword);
    }

        // Retrieve all entries from an Organizational Unit (OU)
    public List<User> getAllUsersFromOu(String ouName) {
        LdapQuery query = LdapQueryBuilder.query()
                .base("ou=bolu")  // Set the Organizational Unit (OU) to search in
                .where("objectClass").is("person");  // Filter to retrieve only person entries
        System.out.println(query.base().toString());
        return ldapTemplate.search(query, (AttributesMapper<User>) attrs -> {
            String name = (attrs.get("cn") != null) ? attrs.get("cn").get().toString() : "Unknown";
            String password = (attrs.get("password") != null) ? attrs.get("password").get().toString() : "NoPassword"; 
            System.out.println(password);           
            User user = new User(name, password);
            return user;
        });
    }

    public void authenticate(String username, String password) {
        contextSource
          .getContext(
            "cn=" + 
             username + 
             ",ou=users,dc=auth,dc=project,dc=com", password);
    }
}