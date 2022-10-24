<?php

namespace App\Event;

use App\Nft;
use App\User\User;
use SplObserver;
use SplSubject;

class EmailNotificationListener implements SplObserver
{

    private User $user;
    private Nft $nft;

    /**
     * @param User $user
     * @param Nft $nft
     */
    public function __construct(User $user, Nft $nft)
    {
        $this->user = $user;
        $this->nft = $nft;
    }


    public function update(SplSubject $subject): void
    {
        echo "Mail à " . $this->user->email . ": Voici votre certificat de propriété : " . $this->nft->nonce . PHP_EOL;

    }
}