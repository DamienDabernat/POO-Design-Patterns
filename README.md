## Design patterns

Quand nous écrivons du code, nous le faisons avec “notre style” ainsi, nous résolvons des problèmes de notre façon avec plus ou moins “d’élégance”. En dehors des critères de performances, une solution simple et claire pour une personne peut paraitre abscons et complexe pour d’autre.
Il faut bien avouer aussi, que, certaines manières de faire sont plus pertinentes que d’autre.

De plus, dans le monde du développement informatique, nous sommes régulièrement confrontés aux mêmes problèmes avec toujours cette nécessité de travailler en groupe.

Alors, comment faire en sorte de se mettre d’accord sur une manière commune de coder afin de répondre aux problèmes courant du développement logiciel ?

En utilisant des Design Pattern. 

Mais avant tout, il faut savoir de quoi on parle :

Le mot Design Pattern est un terme qui provient du Gang Of Four :
Erich Gamma, Richard Helm, Ralph Johnson et John Vlissides qui date de 1994.

Ce 4 ingénieurs qui ont écrit un livre nommé : "Design Patterns: Elements of Reusable Object-Oriented" devenu une référence depuis.

Le livre est devenu si populaire et son concept est tellement parlant que depuis, de nombreux designs pattern qui n’existait pas dans le libre ont émergé.

Comme le nom du livre l'indique, il s'agit bien de programmation orientée objet. Cela veut dire que les différents patterns dont on va parler ici n'ont du sens que dans un contexte de programmation orienté objet.

Les langages les plus communs implémentant le paradigme de l’orienté objet sont : Java, Python, C++, C# et Ruby.

### Comment reconnaitre un langage orienté objet ?

Pour faire court, un langage orienté objet est langage dont la donnée est structurée autours d’agrégats servant à répondre à des questions. Ces agrégats sont appelés des classes.
Ainsi pour une classe “Voiture” par exemple, une classe servira à compter le nombre de roues.

La programmation orienté objet repose sur 4 principes :
- **L’abstraction** : Une classe n’est pas le reflet identique d’un objet réel de la vraie vie. C’est une version simplifiée.
- **L’encapsulation** : Une classe peut nous cacher une partie de son comportement via des méthodes privée et / ou des interfaces.
- **L’héritage** : C’est le fait qu’une classe puisse bénéficier des mêmes attributs et des mêmes fonctions qu’une autre. 
- **Le polymorphisme** : C’est la possibilité pour un objet de se faire passer pour un objet d’un type différent.

Si le langage que vous utilisez ne réponds pas majoritairement à ses grands principes, alors, il n’est possiblement pas orienté objet (cela peut être un langage fonctionnel comme le Clojure par exemple).

Normalement tout cela doit être déjà quelques chose de connu pour vous et dans le cas contraire, je vous invite à d’abord comprendre ce qu’est la programmation orienté objet avant d’attaquer profondément sur les patrons de conception.

### Concrètement, qu’est-ce qu’un Design Pattern ?

Comme dit plus haut, un Design Pattern est façon de se mettre d’accord sur la façon de résoudre les problèmes communs et récurrents.

Un Design Pattern n’est pas une solution toute prête à être implémenté. Il faut voir le Design Pattern plus comme une sorte de guide générique.

Le GoF à donner une définition des Design pattern : 

Un patron de conception nomme, motive et explique systématiquement une conception générale qui répond à un problème de conception récurrent dans les systèmes orientés objet.
Il décrit le problème, la solution, quand appliquer la solution et ses conséquences.
Il donne également des conseils et des exemples d'implémentation. 

La solution est un arrangement général d'objets et de classes qui résout le problème.
La solution est personnalisée et mise en œuvre pour résoudre le problème dans un contexte particulier.

Le contenu d'un design pattern est constitué des éléments suivants :
- Nom
- Résumé
- La problématique détaillée
- Pertinence (conditions d’utilisations) 
- Structure détaillée (les classes et leurs relations)
- Conséquences (positive et négative)
- Exemple d’implémentation de code
- Cas d’usages
- Patrons associés

### Les type de design pattern :

- Les patrons de créations : fournissent des mécanismes de création d'objets qui augmentent la flexibilité et la réutilisation du code existant.
- Les patrons structurels expliquent comment assembler les classes dans des structures plus importantes, en gardant ces structures performantes.
- Les patrons comportementaux assurent une communication efficace entre les classes et permettent de résoudre des problèmes liés aux comportements de celle-ci.

