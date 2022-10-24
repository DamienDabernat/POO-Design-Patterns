package fr.neufplate;

public abstract class Avatar {

    public String url;

    public void generate() {
        AvatarClientInterface client = getClient();
        this.url = client.getRandomAvatarUrl();
    }

    public void generate(String hash) {
        AvatarClientInterface client = getClient();
        this.url = client.getAvatarFromHash(hash);
    }

    protected abstract AvatarClientInterface getClient();

    @Override
    public String toString() {
        return "Avatar{" +
                "url='" + url + '\'' +
                '}';
    }
}