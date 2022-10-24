<?php

namespace App\AvatarProvider\DiceBear;

use App\AvatarClientInterface;
use App\AvatarProvider\SpriteType;

class DiceBearClient implements AvatarClientInterface
{
    private static ?DiceBearClient $_instance = null;

    public string $spriteType;
    private const apiUrl = "https://avatars.dicebear.com/api/";
    private const fileType = ".svg";

    public function __construct()
    {
        $this->spriteType = SpriteType::HUMAN->value;
    }

    public static function getInstance(): AvatarClientInterface {

        if(is_null(self::$_instance)) {
            self::$_instance = new DiceBearClient();
        }

        return self::$_instance;
    }

    // Exercice 1
    public static function randomAvatarUrl(): string
    {
        return self::apiUrl . self::getInstance()->spriteType . "/" . uniqid() . self::fileType . PHP_EOL;
    }

    // Exercice 2
    public function getRandomAvatarUrl(): string
    {
        return self::apiUrl . self::getInstance()->spriteType . "/" . uniqid() . self::fileType . PHP_EOL;
    }

    public function setSpriteType(SpriteType $spriteType): void
    {
        $this->spriteType = $spriteType->value;
    }

    public function getAvatarFromHash(string $hash): string
    {
        return self::apiUrl . "/" . $hash . self::fileType . PHP_EOL;
    }

}