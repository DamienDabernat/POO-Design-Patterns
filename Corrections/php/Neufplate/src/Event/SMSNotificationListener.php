<?php

namespace App\Event;

use App\Nft;
use App\User\User;
use SplObserver;
use SplSubject;

class SMSNotificationListener implements SplObserver
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
        echo "SMS à " . $this->user->phone . ": Voici votre certificat de propriété : " . $this->nft->nonce . PHP_EOL;
    }
}