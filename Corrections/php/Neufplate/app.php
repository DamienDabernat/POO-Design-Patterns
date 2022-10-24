<?php

use App\AvatarProvider\DiceBear\DiceBearAvatar;
use App\AvatarProvider\DiceBear\DiceBearClient;
use App\AvatarProvider\Provider;
use App\AvatarProvider\ProviderType;
use App\AvatarProvider\RobotHash\RobotHashAvatar;
use App\AvatarProvider\SpriteType;
use App\Neufplate;
use App\Nft;
use App\User\UserBuilder;

require __DIR__ . '/vendor/autoload.php';

// ----------
// Exercise 1
echo DiceBearClient::randomAvatarUrl();
echo DiceBearClient::randomAvatarUrl();

// Version de base
DiceBearClient::getInstance()->spriteType = SpriteType::AVATAAARS->value;

//Version de ouf
DiceBearClient::getInstance()->setSpriteType(SpriteType::AVATAAARS);

echo DiceBearClient::randomAvatarUrl();
echo DiceBearClient::randomAvatarUrl();


// ----------
// Exercise 2
$avatar = new DiceBearAvatar(SpriteType::FEMALE);
$avatar->generate();
echo $avatar->url;

$avatar = new RobotHashAvatar();
$avatar->generate();
echo $avatar->url;

//--------
// Exercise 3
$builder = new UserBuilder();
$user = $builder
    ->addNames('John', 'Doe')
    ->addAddress("Fake address 1234")
    ->addEmail("damien@dabernat.fr")
    ->build();

//--------

$neufplate = new Neufplate();
$provider = new Provider(ProviderType::DICEBEAR, SpriteType::BOTTTS);
$nft = $neufplate->process($user, $provider);
echo $nft->getPrice();