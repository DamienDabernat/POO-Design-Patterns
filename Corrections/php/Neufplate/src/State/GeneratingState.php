<?php

namespace App\State;

use App\AvatarProvider\DiceBear\DiceBearAvatar;
use App\AvatarProvider\Provider;
use App\AvatarProvider\ProviderType;
use App\AvatarProvider\RobotHash\RobotHashAvatar;
use App\Neufplate;

class GeneratingState extends State
{

    public function __construct(Neufplate $neufplate)
    {
        parent::__construct($neufplate);
    }

    public function onTitling(): ?string
    {
        return null;
    }

    public function onMakingCollision(): ?string
    {
        return null;
    }

    public function onGenerating(): ?string
    {
        $avatar = null;
        switch ($this->neufplate->provider->type) {
            case ProviderType::DICEBEAR:
                $avatar = new DiceBearAvatar();
                break;
            case ProviderType::ROBOTHASH:
                $avatar = new RobotHashAvatar();

        }
        $avatar->generate($this->neufplate->nft->getHash());
        $this->neufplate->nft->avatar = $avatar;
        return $avatar->url;
    }
}