/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import com.novell.ldap.LDAPConnection;
import java.io.Serializable;

/**
 *
 * @author juan
 */
public class GlobalConfig implements Serializable {

    public static final String PERSISTENCE_UNIT = "automarketPU";
    public static int session_counter;
    public static final String LDAP_HOST = "192.168.2.135";
    public static final String DN = "cn=admin,dc=automarket,dc=com";
    public static final String LDAP_PASSWORD = "12345";
    public static final int LDAP_PORT = LDAPConnection.DEFAULT_PORT;
    public static final int LDAP_VERSION = LDAPConnection.LDAP_V3;
    public static final String GROUP_DN = "ou=Sector-Mayorista,dc=automarket,dc=com";

}
