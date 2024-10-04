package io.codemalone33.springboot.error.springboot_error.models;

public class User {

    private Long id;
    private String name;
    private String email;
    private Role role;

    public User() {
    }
    
    public User(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

   /*  public String getRoleName() {
        return role.getName();
    } */

    public void setRole(Role role) {
        this.role = role;
    }

    

}
