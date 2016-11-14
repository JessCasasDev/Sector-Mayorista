/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic.controller;

import com.novell.ldap.LDAPAttribute;
import com.novell.ldap.LDAPAttributeSet;
import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPEntry;
import com.novell.ldap.LDAPException;
import com.novell.ldap.LDAPModification;
import java.io.UnsupportedEncodingException;
import static config.GlobalConfig.*;
import java.io.Serializable;

/**
 *
 * @author root
 */
public class LDAPAutomarket implements Serializable{

    private LDAPConnection lc = new LDAPConnection();

    public String login2(String user, String password) {
        if (connect()) {
            if (validatePassword(user, password)) {
                return "true";
            } else {
                return "Usuario y/o contraseña incorrectos.";
            }
        } else {
            return "Conexión al servidor LDAP fallida.";
        }
    }

    public Boolean connect() {
        try {
            lc.connect(LDAP_HOST, LDAP_PORT);
            System.out.println("==== Conectado al servidor LDAP ====");
            lc.bind(LDAP_VERSION, DN, LDAP_PASSWORD.getBytes("UTF8"));
            System.out.println("==== Autenticado en el servidor ====");
            return true;
        } catch (LDAPException | UnsupportedEncodingException ex) {
            System.out.println("==== ERROR al conectarse al servidor LDAP ====");
            return false;
        }

    }

    public Boolean validatePassword(String user, String password) {

        String dn = "cn=" + user + "," + GROUP_DN;

        try {
            lc.bind(dn, password);
            System.out.println("==== Contraseña validada ====");
            return true;
        } catch (LDAPException ex) {
            System.out.println("==== ERROR al validar la contraseña ====");
            return false;
        }
    }

    public boolean addUser(String userName, String password, String name) {
        Boolean connect = connect();
        if (!connect) {
            return false;
        }
        String containerName = "ou=Sector-Mayorista";
        LDAPAttribute attribute = null;
        LDAPAttributeSet attributeSet = new LDAPAttributeSet();

        /* To Add an entry to the directory,

         *  - Create the attributes of the entry and add them to an attribute set

         *  - Specify the DN of the entry to be created

         *  - Create an LDAPEntry object with the DN and the attribute set

         *  - Call the LDAPConnection add method to add it to the directory

         */
        attributeSet.add(new LDAPAttribute(
                "objectclass", "inetOrgPerson"));

        attributeSet.add(new LDAPAttribute("cn",
                userName));

        attributeSet.add(new LDAPAttribute("givenname",
                name));

        attributeSet.add(new LDAPAttribute("sn", name));

        attributeSet.add(new LDAPAttribute("userpassword",
                password));

        String dn = "cn=" + userName + "," + GROUP_DN;

        LDAPEntry newEntry = new LDAPEntry(dn, attributeSet);

        try {

            lc.add(newEntry);

            System.out.println("\nAdded object: " + dn + " successfully.");

            // disconnect with the server
            lc.disconnect();

        } catch (LDAPException e) {
            System.out.println("Error:  " + e.toString());
            return false;

        }
        return true;
    }

    public void doCleanup(String userdn) {
        String groupdn = GROUP_DN;
        // since we have modified the user's attributes and failed to
        // modify the group's attribute, we need to delete the modified
        // user's attribute values.
        // modifications for user
        LDAPModification[] modUser = new LDAPModification[2];

        // Delete the groupdn from the user's attributes
        LDAPAttribute membership = new LDAPAttribute("groupMembership", groupdn);

        modUser[0] = new LDAPModification(LDAPModification.DELETE, membership);

        LDAPAttribute security = new LDAPAttribute("securityEquals", groupdn);

        modUser[1] = new LDAPModification(LDAPModification.DELETE, security);

        try {

            // Modify the user's attributes
            lc.modify(userdn, modUser);

            System.out.println("Deleted the modified user's attribute values.");

        } catch (LDAPException e) {

            System.out.println(
                    "Could not delete modified user's attributes: " + e.toString());

        }

        return;
    }
}
