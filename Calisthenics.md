#### Intro by https://github.com/hhamon/lpdim2016/blob/master/README.md

Gymnastique des Objets
----------------------

La gymnastique des objets ou « *Objects Calisthenics* » en anglais est une série
d'exercices pour s'entraîner à améliorer la qualité générale du code que l'on
écrit. Il s'agit de neuf règles de codage à suivre pour rendre le code meilleur.
Cette série d'exercices de gymnastique intellectuelle a été inventée par Jeff
Bay dans son ouvrage [ThoughtWorks Anthology](https://pragprog.com/book/twa/thoughtworks-anthology),
publié dans les éditions *The Pragmatic Programmers*. Ces règles sont
universelles et agnostiques du langage de programmation employé. Il s'agit de
les mettre en pratique quel que soit le langage de programmation orienté objet
utilisé. Les neuf règles de la gymnastique des objets sont décrites ci-dessous.

### 1. Seulement un niveau d'indentation

Pour améliorer la lisibilité du code, sa testabilité, sa maintenabilité et sa
réutilisabilité, il est recommandé de ne pas dépasser plus d'un niveau
d'indentation par fonction. Le code qui se trouve dans chaque niveau
supplémentaire peut être déporté dans une nouvelle fonction. Le code ci-après
montre une fonction qui ne respecte pas encore cette règle.

```php
class RouteCollection
{
    public function merge(RouteCollection $routes, $override = false)
    {
        foreach ($routes as $name => $route) {
            if (isset($this->routes[$name]) && !$override) {
                throw new \InvalidArgumentException(sprintf(
                    'A route already exists for the name "%s".',
                    $name
                ));
            }

            $this->routes[$name] = $route;
        }
    }
}
```

Dans cet exemple, on constate aisément que la méthode `merge()` de la classe
`RouteCollection` dispose de deux niveaux d'indentation. La première indentation
devant le mot-clé `foreach` compte pour un niveau 0. Par conséquent, le code à
l'intérieur de la boucle doit être extrait et encapsulé dans une méthode dédiée
comme le montre le listing ci-dessous :

```php
class RouteCollection
{
    public function merge(RouteCollection $routes, $override = false)
    {
        foreach ($routes as $name => $route) {
            $this->add($name, $route, $override);
        }
    }

    public function add($name, Route $route, $override = false)
    {
        if (isset($this->routes[$name]) && !$override) {
            throw new \InvalidArgumentException(sprintf(
                'A route already exists for the name "%s".',
                $name
            ));
        }

        $this->routes[$name] = $route;
    }
}
```

Avec ce changement simple, la méthode `merge()` ne révèle plus qu'un seul niveau
d'indentation et il en va de même pour la méthode `add()`. Si cette dernière
avait eu plus d'un niveau d'indentation, alors il aurait fallu aussi la remanier
pour la simplifier et ainsi de suite jusqu'à obtenir un seul niveau
d'indentation partout. Cette règle de gymnastique encourage non seulement à
produire du code plus lisible mais c'est aussi un moyen de favoriser l'écriture
de toutes petites fonctions ayant qu'une seule responsabilité.

### 2. Éviter l'usage du mot-clé `else`

Cette règle est simple ! Il s'agit de bannir autant que faire se peut l'usage du
mot-clé `else` dans les structures conditionnelles interrompant l'exécution du
programme le plus tôt possible. Il existe trois manières de s'éviter l'usage du
mot-clé `else` dans une fonction.

#### Utiliser une instruction `return`

```php
class ServiceLocator implements ServiceLocatorInterface
{
    public function getService($name)
    {
        // Try to leave the function as early as possible
        // The service is already created, so return it
        // immediately and skip the rest of the method.
        if (isset($this->services[$name])) {
            return $this->services[$name];
        }

        if (!isset($this->definitions[$name])) {
            throw new \InvalidArgumentException(sprintf(
                'No registered service definition called "%s" found.',
                $name
            ));
        }

        $this->services[$name] = $service = call_user_func_array($this->definitions[$name], [$this]);

        return $service;
    }
}
```

#### Lever une exception.

```php
class ServiceLocator implements ServiceLocatorInterface
{
    public function register($name, \Closure $definition)
    {
        // Try to leave the function as early as possible
        // Throw an exception whenever a method requirement
        // is not met.
        if (!is_string($name)) {
            throw new \InvalidArgumentException('Service name must be a valid string.');
        }

        $this->definitions[$name] = $definition;

        return $this;
    }
}
```

#### Injecter une stratégie dite « Null Object ».

Lorsqu'un objet dépend d'un collaborateur facultatif, il est fréquent de tester
à l'exécution du programme si ce collaborateur n'est pas nul avant d'appeler une
méthode sur celui-ci. Le snippet ci-après dévoile un exemple de collaborateur
facultatif.

```php
namespace Application;

use Framework\Http\Response;
use Framework\Routing\Exception\RouteNotFoundException;
use Framework\Templating\ResponseRendererInterface;
use Psr\Log\LoggerInterface;

class ErrorHandler
{
    private $logger;
    private $renderer;

    public function __construct(ResponseRendererInterface $renderer, LoggerInterface $logger = null)
    {
        $this->renderer = $renderer;
        $this->logger = $logger;
    }

    public function handleException(\Exception $exception)
    {
        // Check if logger is injected before logging anything.
        if (null !== $this->logger) {
            $this->logger->critical($exception->getMessage());
        }

        $vars = ['exception' => $exception];
        if ($exception instanceof RouteNotFoundException) {
            return $this->render('errors/404.twig', $vars, Response::HTTP_NOT_FOUND));
        }

        // ...
    }
}
```

Dans cet exemple, la fonction `handleException()` teste l'existence de l'objet
`logger`. S'il existe, alors sa méthode `critical()` est appelée pour
enregistrer une erreur critique dans les journaux d'erreur. Ici, c'est encore
acceptable car le nombre de conditions de ce type est limité. Néanmoins, l'idéal
consiste à rendre la dépendance obligatoire tel que l'illustre le code
ci-dessous :

```php
class ErrorHandler
{
    // ...

    public function __construct(ResponseRendererInterface $renderer, LoggerInterface $logger)
    {
        $this->renderer = $renderer;
        $this->logger = $logger;
    }

    public function handleException(\Exception $exception)
    {
        $this->logger->critical($exception->getMessage());

        // ...
    }
}
```

L'interface `LoggerInteface` est ici une implémentation du patron « Stratégie ».
Il est donc maintenant aisé de proposer différentes implémentations de celle-ci
selon l'environnement d'exécution de l'application. En environnement de
développement, une véritable implémentation de journal d'erreurs sera injectée
tandis que dans un mode de production, il s'agira de passer un objet « logger »
null tel que celui présenté ci-après.

```php
namepace Framework\Logger;

use Psr\Log\LoggerInterface;

class NullLogger implements LoggerInterface
{
    public function critical($message, array $context = [])
    {
        // ... do nothing!
    }
}
```

Au final, la classe `ErrorHandler` n'a plus besoin d'être polluée par de
multiples conditions inutiles. L'implémentation du patron d'architecture
« *Null Object* » élimine les conditions et rend ainsi le code plus lisible et
plus facilement testable. En effet, en éliminant la condition, il ne reste plus
qu'un seul et unique chemin d'exécution pour le code contre deux auparavant. Il
faut donc écrire un seul test unitaire pour couvrir le code au lieu de deux.
Grâce au patron « *Null Object* », le code s'utilise de la manière suivante :

```php

$twig = new \Twig_Environment(new \Twig_Loader_Filesystem(__DIR__.'/views'));
$renderer = new TwigRendererAdapter($twig);

// Development mode with a real logger
$errorHandler = new ErrorHandler($renderer, new RealLogger(__DIR__.'/logs/dev.log'));
$errorHandler->handleException($exception);

// Production mode with a null logger
$errorHandler = new ErrorHandler($renderer, new NullLogger());
$errorHandler->handleException($exception);
```

Les bénéfices directs de cette règle d'évitement du mot-clé `else` pour la
qualité du code sont une meilleure lisibilité, une diminution du code dupliqué
et des complexités cyclomatiques du code plus faibles. En effet, moins il y a de
chemins d'exécution que peut prendre le code et plus faibles sont les
complexités cyclomatiques des fonctions.

### 3. Encapsuler les types primitifs

L'idée de cette règle consiste à encapsuler les types primitifs scalaires
(`int` `bool`, `float`, `string`, etc.) dans des classes afin de forcer des
types orientés objets. Cette règle est toutefois largement discutable pour un
langage tel que PHP dont la philosophie même est d'offrir un typage dynamique.

Cependant, elle trouve du sens à partir du moment où l'on cherche à garantir une
cohérence et une forte cohésion des types du programme. Une classe `String` qui
encapsule une valeur scalaire de chaîne de caractères peut aussi offrir des
méthodes de manipulation de la chaîne (`toLower()`, `toUpper()`, `explode()`,
etc.) plutôt que des fonctions natives PHP.

Encapsuler des types primitifs dans des objets a un véritable intérêt quand il
s'agit de regrouper des données communes à un même concept et d'offrir des
méthodes de manipulation de ces données. Un excellent exemple est celui d'une
classe `Money` qui encapsule les attributs d'une valeur fiduciaire (la somme
d'argent et le code ISO de la devise) ainsi que les opérations possibles sur
cette monnaie.

```php
$money1 = Money::createFromString('1 725.78 EUR');
$money2 = Money::createFromString('12.22 EUR');

$money3 = $money1->add($money2);
$money4 = $money3->multiply(10);

// Split the amount of money in 5 equal shares.
$shares = $money4->split(5);
```

Encapsuler les données et opérations de manipulation d'une devise monétaire dans
une classe simplifie leur usage par rapport à de simples variables scalaires et
fonctions PHP.

### 4. Maximum un opérateur d'objet par ligne

Cette règle stipule qu'il ne doit pas y avoir plus d'un opérateur d'objet par
ligne. En d'autres termes, le symbole `->` ne doit pas être utilisé plus d'une
fois sur une ligne.

Dit autrement, un objet doit communiquer avec son collaborateur le plus proche
et non les collaborateurs de ses collaborateurs. C'est ce que l'on appelle plus
communément la « Loi de Déméter » ou principe de connaissance minimale.

Dans la méthode `addPiece()` de la classe `ChessBoard` ci-dessous, l'échiquier
a bien trop de connaissances d'implémentation de la matrice. Ce code montre un
couplage fort entre l'échiquier et les détails d'implémentation comme les objets
de rangée (`Row`) et de colonne (`Column`).

```php
class ChessBoard
{
    private $matrix;

    public function addPiece($x, $y, Piece $piece)
    {
        $this->matrix->getRow($x)->getColumn($y)->add($piece);
    }
}
```

Idéalement, l'échiquier ne devrait parler qu'à son collaborateur le plus proche,
l'objet `Matrix` tel qu'illustré ci-dessous.

```php
class ChessBoard
{
    private $matrix;

    public function addPiece($x, $y, Piece $piece)
    {
        $this->matrix->add($x, $y, $piece);
    }
}
```

De même, l'objet `Matrix` ne doit communiquer qu'avec ses collaborateurs les
plus proches, les objets de rangées.

```php
class Matrix
{
    private $rows;

    public function add($x, $y, Piece $piece)
    {
        $this->getCell($x, $y)->add($piece);
    }

    public function getCell($x, $y)
    {
        return $this->rows[$x]->getColumn($y);
    }
}
```

Enfin, l'objet `Row` ne doit communiquer qu'avec ses collaborateurs les
plus proches, les objets de colonnes.

```php
class Row
{
    private $columns;

    public function getColumn($y)
    {
        return $this->columns[$y];
    }

    public function addPieceOnCell($y, Piece $piece)
    {
        $this->getColumn($y)->add($piece);
    }
}
```

En validant cette règle, le code devient plus lisible mais surtout plus
facilement testable. En effet, dans un test unitaire, il suffit alors de créer
l'objet que l'on teste ainsi que ses collaborateurs les plus proches. Il n'y a
plus besoin de connaître les collaborateurs des collaborateurs.

> Contrairement à Java où le mot-clé `this` est facultatif, la variable `$this`
> est obligatoire en PHP pour référencer l'objet courant. Par conséquent, on
> accepte jusqu'à deux opérateurs d'instance par ligne au lieu d'un pour valider > cette quatrième règle de gymnastique des objets.

### 5. Ne pas abréger

Il est loin le temps où les espaces de mémoire vive et de stockage étaient
limités et se comptaient en quelques octets, voire kilo-octets. Inutile donc
d'abréger les noms des variables, des méthodes et des classes. Des noms bien
choisis pour les structures de données favorisent largement la lisibilité et la
compréhension du code par ses pairs.

Il faut toujours garder à l'esprit que l'on écrit d'abord du code pour soi-même
et pour les autres, et non pour la machine. Une citation est d'ailleurs très
célèbre dans le monde d'informatique.

> There are only two hard things in Computer Science: cache invalidation and
> naming things.
>
> -- *Phil Karlton*

Traduite littéralement en français, cette citation signifie qu'*il existe
seulement deux choses difficiles à réaliser en ingénierie informatique :
invalider des caches et savoir nommer les choses correctement.*

### 6. Développer des petites classes

Ce principe est tout simplement une réciproque du principe de responsabilité
unique de SOLID. Il s'agit de développer de petites classes ne dépassant pas un
certain seuil de nombre de lignes de code. Idéalement, il convient de ne pas
dépasser entre 150 et 200 lignes de code par classe en fonction de la verbosité
du langage de programmation utilisé.

En suivant ce principe, cela force le développeur à remanier régulièrement son
code afin d'extraire dans de nouvelles classes plus petites des responsabilités
identifiées. Le nombre de lignes de code dans une classe ainsi que le nombre de
ses attributs et de ses collaborateurs donnent facilement des indices sur le
niveau de complexité de celle-ci. Plus il y a de méthodes, d'attributs et de
collaborateurs, et plus il y a de risques pour que la classe ait plus d'une
responsabilité.

### 7. Limiter le nombre de propriétés d'instance dans une classe

Dans son ouvrage, Jeff Bay recommande de ne pas définir plus de deux variables
d'instance par classe. Pourquoi deux et pas trois, cinq ou dix ? Moins il y en a
dans la classe et plus cela force le développeur à mieux encapsuler les autres
attributs dans d'autres classes. Ainsi chaque classe encapsule de manière
atomique un concept, une règle métier ou une responsabilité.

Il est bien sûr très difficile en PHP de respecter cette règle. Le plus
important c'est de définir un seuil raisonnable du nombre maximum d'attributs
d'instance par classe, et le respecter autant de fois que c'est possible. Au
final, il y aura toujours des classes qui valideront ce seuil et d'autres qui le
dépasseront. Un maximum de cinq à sept propriétés d'instance maximum par classe
est un seuil raisonnable dans la plupart des cas.

### 8. Éviter les classes de collection de premier ordre

Lorsqu'il s'agit de manipuler des listes d'objets, il est recommandé d'avoir
recours à des objets de collection plutôt que de simples tableaux. Les classes
de collections génériques, dites de premier ordre, ne sont pas conseillées. Par
exemple, une classe générique nommée `Collection` ou `List`.

Il est en fait plutôt conseillé de créer une classe de collection spécifique
pour chaque type d'objet qu'elle encapsule. Ainsi, la classe de collection
spécifique contrôlera qu'elle reçoit bien des objets qu'elle supporte. De plus,
la collection offrira des méthodes spécifiques pour filtrer les éléments, leur
appliquer des opérations en lot ou bien en extraire certains qui correspondent à
un critère spécifique.

```php
namespace Framework\Routing;

class RouteCollection
{
    /** @var Route[] */
    private $routes;

    public function __construct()
    {
        $this->routes = [];
    }

    public function getName(Route $route)
    {
        foreach ($this->routes as $name => $oneRoute) {
            if ($route === $oneRoute) {
                return $name;
            }
        }

        throw new \RuntimeException('Route is not registered in the collection.');
    }

    public function merge(RouteCollection $routes, $override = false)
    {
        if ($routes === $this) {
            throw new \LogicException('A routes collection cannot merge into itself.');
        }

        foreach ($routes as $name => $route) {
            $this->add($name, $route, $override);
        }
    }

    public function match($path)
    {
        foreach ($this->routes as $route) {
            if ($route->match($path)) {
                return $route;
            }
        }
    }

    public function add($name, Route $route, $override = false)
    {
        if (isset($this->routes[$name]) && !$override) {
            throw new \InvalidArgumentException(sprintf(
                'A route already exists for the name "%s".',
                $name
            ));
        }

        $this->routes[$name] = $route;
    }
}
```

La classe `RouteCollection` ci-dessus extraite du mini-framework développé en
cours est un exemple d'implémentation de cet exercice de gymnastique qui porte
sur les collections. Ici, la classe `RouteCollection` traite de manière uniforme
des objets de type `Route`. Elle offre des mécanismes pour ajouter de nouvelles
routes à la collection, fusionner deux collections entre elles ou bien
rechercher l'objet de route qui correspond au critère spécifié.

### 9. Limiter l'usage d'accesseurs et mutateurs publiques

Afin de garantir le principe d'encapsulation du paradigme orienté objet, les
bonnes pratiques recommandent généralement de définir les attributs d'un objet
avec une portée privée, voire protégée. Cela limite ainsi la portée de
l'attribut uniquement à la classe dont il est issu, voire aux classes dérivées
dans le cadre d'une portée protégée. En revanche, l'accès direct aux attributs
depuis l'extérieur de l'objet est ainsi strictement interdit.

Dans ces conditions, l'objet agit donc comme une boîte noire dont les composants
internes ne sont pas visibles de l'extérieur par les entités qui le manipulent.
Seules des méthodes publiques de l'objet permettent au code extérieur de lire
l'état de celui-ci ou bien de le modifier.

Bien qu'un attribut est défini avec une portée privée, l'erreur consiste à lui
associé un couple d'accesseur et mutateur publiques. Les accesseurs sont les
méthodes dites « *getter* » et les mutateurs sont les méthodes
traditionnellement dénommées « *setter* ». Le snippet ci-dessous montre un
exemple de classe qui expose à outrance ce type de méthodes.

```php
class BankAccount
{
    private $amount;
    private $currency;

    public function __construct($initialBalance, $currency)
    {
        $this->amount = $initialBalance;
        $this->currency = $currency;
    }

    public function getCurrency()
    {
        return $this->currency;
    }

    public function setCurrency($currency)
    {
        $this->currency = $currency;
    }

    public function setBalance($newAmount)
    {
        $this->amount = $newAmount;
    }

    public function getBalance()
    {
        return $this->amount;
    }
}
```

Exposer un couple de méthodes *getter* et *setter* pour chaque attribut privé
revient quelque part à le rendre publique et donc violer le principe
d'encapsulation. L'état de l'objet peut ainsi être dévoilé et modifié par le
code client sans réel contrôle comme le montre le listing ci-dessous.

```php
$account = new BankAccount(500, 'EUR');

// Owner has just deposited 300 on his\her bank account
$account->setBalance($account->getBalance() + 300);

// Owner has just withdrawed 150 from his\her bank account
$account->setBalance($account->getBalance() - 150);

// Owner has changed the currency of his\her bank account
$account->setCurrency('USD');
```

Comme le montre le code ci-dessus, le solde du compte bancaire est publiquement
dévoilé par le *getter* mais il est aussi sauvagement modifié par le *setter*
sans aucun contrôle

Par exemple, que se passerait-il si le possesseur du compte bancaire souhaite
retirer 150 EUR bien que son compte bancaire ne dispose que d'un solde de 100
EUR ? Au niveau du programme, il est semblerait légitime de se demander si cette
opération est acceptable ou non en fonction de la politique de la banque qui
tient le compte. La banque autorise-t-elle une facilité de caisse pendant une
période de découvert ? Si oui, jusqu'à quel montant ? Le code actuel ne permet
pas de le prendre en compte.

La première instruction quant à elle semble montrer que le client du compte
bancaire vient de déposer 300 sur son compte. Hors, s'agit-il de 300 EUR, 300
USD ou bien 300 cacahuètes ? Le risque ici c'est de se retrouver avec un nouveau
solde de compte bancaire incohérent !

Enfin, la dernière instruction modifie la devise du compte bancaire. Par
conséquent, le solde du compte bancaire initialement en Euro passe subitement en
Dollars américains. Avec un taux de change défavorable, le client du compte
bancaire se retrouve lésé par sa banque ! Cette opération ne devrait d'ailleurs
pas être possible. En définitive, cette méthode ne devrait jamais être définie
dans la classe `BankAccount` avec une portée publique.

Cette neuvième et dernière règle de gymnastique des objets recommande donc
d'éviter d'exposer publiquement des méthodes accesseurs et mutateurs sur
l'objet. À la place, l'objet doit proposer uniquement des méthodes utiles pour
le manipuler et contrôler son nouvel état comme le montre la nouvelle classe
ci-dessous.

```php
class BankAccount
{
    private $balance;
    private $maxAllowedOverdraft;

    private function __construct(Money $initialBalance, Money $maxAllowedOverdraft)
    {
        static::compareMoney($initialBalance, $maxAllowedOverdraft);

        $this->balance = $initialBalance;
        $this->maxAllowedOverdraft = $maxAllowedOverdraft;
    }

    public static function open($initialBalance, $maxAllowedOverdraft)
    {
        return new self(
            Money::fromString($initialBalance),
            Money::fromString($maxAllowedOverdraft)
        );
    }

    public function getBalance()
    {
        return $this->balance;
    }

    public static function compareMoney(Money $money1, Money $money2)
    {
        if (!$money1->isSameCurrency($money2)) {
            throw new CurrencyMismatchException($money1->getCurrency(), $money2->getCurrency());
        }
    }

    public function deposit($amount)
    {
        $deposit = Money::fromString($amount);

        // Check both current money and given money are compatible together
        static::compareMoney($this->balance, $deposit);

        // Update new balance
        $this->balance = $this->balance->add($deposit);

        // Return new actual balance after money deposit
        return $this->balance;
    }

    public function withdraw($amount)
    {
        $withdrawal = Money::fromString($amount);

        // Check both current money and given money are compatible together
        static::compareMoney($this->balance, $withdrawal);

        // Check the bank account overdraft allowance
        $newBalance = $this->balance->subtract($withdrawal);
        $minAllowedBalance = $this->maxAllowedOverdraft->negate();
        if ($newBalance->lowerThan($minAllowedBalance)) {
            throw new MaxAllowedOverdraftReachedException()
        }

        // Update new balance
        $this->balance = $newBalance;

        // Return new actual balance after money withdrawal
        return $this->balance;
    }
}
```

Dans cette nouvelle classe, les anciens attributs primitifs `$amount` et
`$money` ont été regroupés au sein d'une même classe `Money` afin de valider la
règle #3 (*Encapsuler les types primitifs*). Cette classe `Money` offre ainsi
toute une palette de nouvelles méthodes pour comparer et modifier des valeurs
fiduciaires.

De plus, la portée du constructeur de la classe `BankAccount` a été transformée
en privé afin de forcer l'usage du constructeur statique `BankAccount::open()`.
Ce dernier construit des objets et initialise des objets `Money` à partir de
chaînes de caractères. Le véritable constructeur contrôle quant à lui que ces
paramètres sont cohérents pour compléter la construction de l'objet
`BankAccount`. En d'autres termes, la devise du solde initial du compte bancaire
et la devise du montant autorisé du découvert doivent être identiques.

Le choix de la méthode statique intitulée `open()`, c'est pour mieux
correspondre au vocabulaire du monde bancaire. En effet, ce n'est pas le client
qui « crée » un compte bancaire chez sa banque mais c'est elle qui lui « ouvre »
un nouveau compte.

```php
$account = BankAccount::open('500.00 EUR', '1000.00 EUR');
```

Une fois le compte bancaire créé, son possesseur peut alors soit déposer de
l'argent dessus ou bien en retirer. L'accesseur `getBalance()` et son mutateur
`setBalance()` associés ont disparu au profit de deux nouvelles méthodes plus
expressives : `deposit()` et `withdraw()`.

```php
$account = BankAccount::open('500.00 EUR', '1000.00 EUR');
$account->deposit('250.50 EUR');
$account->withdraw('62.00 EUR');
$account->withdraw('1250.00 EUR');
```

Les deux méthodes `deposit()` et `withdraw()` créent des instances de `Money` à
partir des représentations en chaînes de caractères des montants à créditer ou
débiter du compte. Puis, elles comparent que ces montants à créditer ou débiter
du compte bancaire sont cohérents. Leur devise doit en effet correspondre à la
devise du solde actuel du compte bancaire.

Une fois ce contrôle effectué, le nouveau montant d'espèces déposé est ajouté au
solde actuel du compte bancaire. L'état du nouveau solde est mis à jour puis
retourné en sortie de la fonction `deposit()`.

Dans le cas du retrait d'argent, le montant à débiter est en plus validé à
condition que la somme débitée n'entraîne pas un dépassement du maximum autorisé
de découvert. Si tout est bon, alors le montant est débité du compte bancaire et
le nouveau solde est mis à jour avant d'être retourné en sortie de la fonction
`withdraw()`.
