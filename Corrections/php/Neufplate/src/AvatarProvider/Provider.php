<?php

namespace App\AvatarProvider;

class Provider
{

    public ProviderType $type;
    public SpriteType $spriteType;

    /**
     * @param ProviderType $type
     * @param SpriteType $spriteType
     */
    public function __construct(ProviderType $type, SpriteType $spriteType)
    {
        $this->type = $type;
        $this->spriteType = $spriteType;
    }

}