package fr.neufplate.User;

public class UserBuilder implements Builder
{
    private User user;

    public UserBuilder() {
        this.reset();
    }

    public void reset() {
        this.user = new User();
    }

    @Override
    public UserBuilder addNames(String firstname, String lastname) {
        this.user.firstName = firstname;
        this.user.lastName = lastname;
        return this;
    }

    @Override
    public UserBuilder addEmail(String email) {
        this.user.email = email;
        return this;
    }

    @Override
    public UserBuilder addPhone(String phone) {
        this.user.phone = phone;
        return this;
    }

    @Override
    public UserBuilder addAddress(String address) {
        this.user.address = address;
        return this;
    }

    public User build() {
        validateUserObject(user);
        User result = this.user;
        this.reset();
        return result;
    }
    private void validateUserObject(User user) {

        if(this.user.firstName.isEmpty() && this.user.lastName.isEmpty()) {
            throw new RuntimeException("User must have a firstname and lastname");
        }

        if(user.phone == null && user.email == null) {
            throw new RuntimeException("Phone or email is required");
        }
    }


}