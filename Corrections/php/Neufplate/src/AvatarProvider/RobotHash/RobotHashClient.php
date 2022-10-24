<?php

namespace App\AvatarProvider\RobotHash;

use App\AvatarClientInterface;
use App\AvatarProvider\SpriteType;

class RobotHashClient implements AvatarClientInterface
{
    private static ?RobotHashClient $_instance = null;

    public string $spriteType;
    private const apiUrl = "https://robohash.org";
    private const fileType = ".png";

    public function __construct()
    {
        $this->spriteType = SpriteType::DEFAULT->value;
    }

    public static function getInstance(): AvatarClientInterface {

        if(is_null(self::$_instance)) {
            self::$_instance = new RobotHashClient();
        }

        return self::$_instance;
    }

    // Exercice 2
    public function getRandomAvatarUrl(): string
    {
        return self::apiUrl . "/" . uniqid() . self::fileType . PHP_EOL;
    }

    public function getAvatarFromHash(string $hash): string
    {
        return self::apiUrl . "/" . $hash . self::fileType . PHP_EOL;
    }
}