# Neufplate™

## Préambule 

Le cours va s’articuler comme suit :
- Exposition d’une problématique de code
- Prendre connaissance du design pattern grâce au site d’Alexander Shvets : [Refactorig Guru](https://refactoring.guru/fr). 
- Résoudre la problématique en appliquant le patron de conception dans les deux langage choisi.

---

Vous travaillez pour l'entreprise `Neufplate™` . 

C'est votre 1er jour, vous travaillez en tant que developpeur chez `Neufplate™` et vous avez envie de faire vos preuves. 
Armé de vos connaissance vous avez à coeur de programmer de la façon la plus propre possible. 
C'est pourquoi dès que possible vous utilisez les design patterns et autres bonne pratique de developpement.

L'entreprise vous lance un challenge de taille :

En effet, jusqu'a maintenant l'entreprise se contenté de simplement voler les illustrations des autres, mais on lui a récémment appris que cette pratique est en faite illégale.
 
C'est alors que `Neufplate™` à une idée totalement inédite !
Elle va générer des avatars unique de façon aléatoire en fonction d'une chaine de caractères appellé `seed`.

Avec un peu de chance elle pourra même vendre ses avatars comme-ci ils étaient côté en bourse.

## 1 - Le singleton

Pour cette première mission, vous avez repéré sur internet un site qui permet de générer des avatars.
Vous devez donc faire une classe qui fera des appels à cette API de façon régulière.

[Voici la documentation de l’api : `https://avatars.dicebear.com/docs`](https://avatars.dicebear.com/docs)


L’objectif est de créer une classe `DiceBearApi` avec une méthode `getRandomAvatar()` qui permet d’afficher une url qui ressemble à : 

`https://avatars.dicebear.com/api/:sprites/:seed.svg`

Le type de sprite doit être `human` par défaut à l’initialisation de la classe.
Par la suite il pourrat être modifié comme bon nous semble.

Ainsi, tous les appels à la classe `DiceBearClient` doivent retourner le même type de sprite. 
La classe `DiceBearApi` peut être appelé de n’importe ou sans initialisation et sans aucun paramêtre.

L’élement ‘seed’ quant à lui est alétoirement généré à chaque appel de la fonction `GetRandomAvatar()`.

Utilisez le design pattern du singleton pour résoudre ce problème.

Le résultat attendu est simplement l'affichage de l'url dans un terminal.

Voila un exemple de résultat attendu : 

```java
 System.out.println(DiceBearClient.getRandomAvatarUrl()); // https://avatars.dicebear.com/api/human/0.9817086718703704.svg

 // Version de base
 DiceBearClient.getInstance().spriteType = "avataaars";

 //Version de ouf
 DiceBearClient.getInstance().setSpriteType(SpriteType.AVATAAARS);

 System.out.println(DiceBearClient.getRandomAvatarUrl()); // https://avatars.dicebear.com/api/avataaars/0.12462390433499249.svg
```

```php
echo DiceBearClient::getRandomAvatarUrl(); //https://avatars.dicebear.com/api/avataaars/634dc4a687a3c.svg

// Version de base
DiceBearClient::getInstance()->setSpriteType("human");

//Version de ouf
DiceBearClient::getInstance()->setSpriteType(SpriteType::Human);

echo DiceBearClient::getRandomAvatarUrl();
```

[Lien vers Design.guru](https://refactoring.guru/design-patterns/singleton)

## 2 - Factory 

La 1er version de votre générateur d’avatar vous convenait très bien jusque la.
Mais elle à tellement de succès que l’on vous demande si il est possible d'ajouter d’autre fournisseur d’avatar.

Nous ne pouvons pas ajouter du code à la classe déjà existante car ce n’est pas la même API et que ne nous voulons pas complexifier le code existant.

En utilisant le patron de conception Factory vous devez créer le code qui permettra indifféremment de généré un avatar quel que soit le fournisseur que l’ont choisi.

[Voici la documentation de la nouvelle api : `https://robohash.org/`](https://robohash.org)


L’objectif est de créer une classe `RobotHashClient` qui est un singleton avec une méthode `getRandomAvatar()` qui permet d’afficher une url qui ressemble à : 

`https://robohash.org/:seed.png`

Puis de créer les classes factories qui vont permettre de consuitruire n'importe quel type d'avatar.
Voici à quoi peut ressembler l'appel à une des factories :

```php
$avatar = new DiceBearAvatar(SpriteType::FEMALE);
$avatar->generate();
echo $avatar->url;

$avatar = new RobotHashAvatar();
$avatar->generate();
echo $avatar->url;
```

```java
Avatar avatarDiceBear = new DiceBearAvatar(SpriteType.FEMALE);
avatarDiceBear.generate();
System.out.println(avatarDiceBear.url);

Avatar avatarRobot = new RobotHashAvatar();
avatarRobot.generate();
System.out.println(avatarRobot.url);
```

Voici l'interface commune aux deux clients : 

```php
interface AvatarClientInterface {
    public function getRandomAvatarUrl(): string;
}
```

```java
public interface AvatarClientInterface {
    public String getRandomAvatarUrl();
}
```

A chaque nouvel avatar crée une url unique est générée.

💡 Astuce ! La classe Avatar doit ressembler à :

```java
public abstract class Avatar {

    public String url;

    public void generate() {
        AvatarClientInterface client = getClient();
        this.url = client.getRandomAvatarUrl();
    }

    protected abstract AvatarClientInterface getClient();
}
```

```php
abstract class Avatar
{

    public string $url;

    public function generate(): void {
        $client = $this->getClient();
        $this->url = $client->getRandomAvatarUrl();
    }

    protected abstract function getClient(): AvatarClientInterface;

}
```

[Lien vers Design.guru](https://refactoring.guru/fr/design-patterns/factory-method)

## 3 - Builder

Vous décidez de créer des profils utilisateurs afin de mieux trairer les demande de génération d'avatar. En effet l'entreprise `Neufplate™` soit conserver une trace des avatars générés par utilisateur.

Mais en plus, le pôle marketing à quelques exigeance : 
- Tous les champs sont optionnels sauf le nom et le prénom !
- Un utlisateur doit avoir soit un numéro de téléphone soit une adresse mail 

Si l'une de ces deux conditions n'est pas rempli alors la création d'un utilisateur est impossible. 

```java
public class User {
    protected String firstName;
    protected String lastName;
    protected List<Avatar> avatar = new ArrayList<>();
    protected String phone;
    protected String address;
    protected String email;
}
```

```php
class User {
    public string $firstname;
    public string $lastname;
    public array $avatar;
    public string $phone;
    public string $email;
    public string $address;
}
```

Utilisez le design pattern builder pour résoudre ce problème.
Voici le résultat attendu :

```java
User user = new UserBuilder()
        .addNames("John", "Doe")
        .addAddress("Fake address 1234")
        .addPhone("0685858585")
        .build();
```

```php
$builder = new UserBuilder();
$user = $builder
    ->addNames('John', 'Doe')
    ->addAddress("Fake address 1234")
    ->addEmail("damien@dabernat.fr")
    ->build();
```

💡 Astuce ! Voici l'interface `Builder` :

```java
public interface Builder {
    public Builder addNames(String firstname, String lastname);
    public Builder addEmail(String email);
    public Builder addPhone(String phone);
    public Builder addAddress(String address);
}
```

```php
interface Builder
{
    public function addNames(string $firstname, string $lastname): Builder;
    public function addEmail(string $email): Builder;
    public function addPhone(string $phone): Builder;
    public function addAddress(string $address): Builder;
}
```

Ps : Ne pas utiliser de `Director`, dans notre cas ce n'est pas nécéssaire.

[Lien vers Design.guru](https://refactoring.guru/design-patterns/builder)

## 4 - State

Le pole marketing est conquis ! Les ventes explosent !

Mais les ambitions de `Neufplate™` sont illimitée. On vous demande d'aller encors plus loin. 
Votre supérieur l'`Architect Growth Hacker Marketing Account Manager Stagiaire` a une idée !

Pour rendre votre produit plus sexy vous allez lancer l'entreprise dans le monde magique des NFT. 
De plus, il est grand temps de passer à l'étape supérieur et de réfléchir comment produire en masse. 

Voici vos objectifs :
- Créer une classe `Nft`
- Donner un nom a vos Nft grâce à une api client
- Générer un hash unique SHA-1 qui commence par `0000` en fonction du nom généré
- Créer un avatar qui aura pour seed le hash précédement crée
- Orchestrer tout cela grâce au design pattern d'état

```java
public class Nft {
    public String title;
    public String hash;
    public Avatar avatar;
    public User user;
}
```

Voici une superbe API pour générer des titres : https://corporatebs-generator.sameerkumar.website/

Ordre des états :
1. `TitlingState`
2. `MakingCollisionState`
3. `GeneratingState`

```java
public class Neufplate {

    private State state;
    //... Some class attribute

    public Neufplate(//...//) {
        //...Do stuff..//
        this.state = new TitlingState(this);
    }

    public void changeState(State state) {
        this.state = state;
    }

    public State getState() {
        return this.state;
    }
}
```

Pour rappel la société `Neufplate™` a décidé qu'il y aurait collision si le hash commence par `0000`. 
Exemple de méthode permettant de générer un hash en SHA-1 en fonction d'un input de type `String`

```java
static String sha1(String input){
    String sha1 = null;
    try {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(input.getBytes(StandardCharsets.UTF_8));
        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException(e);
    }
    return sha1;
}
```

```php
sha1(string $input)
```

