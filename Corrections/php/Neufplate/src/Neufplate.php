<?php

namespace App;

use App\AvatarProvider\Provider;
use App\State\State;
use App\State\TitlingState;
use App\User\User;

class Neufplate
{

    public State $state;
    public Nft $nft;
    public Provider $provider;
    public User $user;

    public function process(User $user, Provider $provider): Nft {
        $this->nft = new Nft();
        $this->user = $user;
        $this->provider = $provider;

        $this->state = new TitlingState($this);
        $this->state->onTitling();
        $this->state->onMakingCollision();
        $this->state->onGenerating();

        $this->user->nftList[] = $this->nft;
        return $this->nft;
    }

    public function changeState(State $state) {
        $this->state = $state;
    }

}