<?php

namespace App\AvatarProvider\DiceBear;

use App\Avatar;
use App\AvatarClientInterface;
use App\AvatarProvider\SpriteType;


class DiceBearAvatar extends Avatar
{

    private SpriteType $spriteType;

    public function __construct(SpriteType $spriteType = SpriteType::DEFAULT)
    {
        $this->spriteType = $spriteType;
    }


    protected function getClient(): AvatarClientInterface
    {
        $client = DiceBearClient::getInstance();
        $client->setSpriteType($this->spriteType);
        return $client;
    }
}