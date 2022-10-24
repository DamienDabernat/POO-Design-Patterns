<?php

namespace App;

interface AvatarClientInterface
{
    public function getRandomAvatarUrl(): string;

    public function getAvatarFromHash(string $hash): string;

    //public function setSpriteType(SpriteType $spriteType): void;
}