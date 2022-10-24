package fr.neufplate.User;

public interface Builder {
    public Builder addNames(String firstname, String lastname);
    public Builder addEmail(String email);
    public Builder addPhone(String phone);
    public Builder addAddress(String address);
}
