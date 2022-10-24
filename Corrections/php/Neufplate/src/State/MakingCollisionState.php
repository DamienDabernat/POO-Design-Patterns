<?php

namespace App\State;

use App\Event\EmailNotificationListener;
use App\Event\SMSNotificationListener;
use App\Neufplate;

class MakingCollisionState extends State
{

    public function __construct(Neufplate $neufplate)
    {
        parent::__construct($neufplate);
        $this->attachNotification();
    }

    private function attachNotification(): void {
        if(isset($this->neufplate->user->email)) {
            $this->attach(new EmailNotificationListener($this->neufplate->user, $this->neufplate->nft));
        }

        if (isset($this->neufplate->user->phone)) {
            $this->attach(new SMSNotificationListener($this->neufplate->user, $this->neufplate->nft));
        }
    }

    public function onMakingCollision(): ?string
    {
        $hash = self::makeCollision($this->neufplate->nft->title);
        $this->neufplate->nft->setHash($hash);
        $this->notify();
        $this->neufplate->changeState(new GeneratingState($this->neufplate));
        return $hash;
    }

    public function onGenerating(): ?string
    {
        return null;
    }

    public function onTitling(): ?string
    {
        return null;
    }

    private static function makeCollision(string $value): string {
        $nonce = 0;
        //in sha1
        $hash = sha1($nonce . "#" . $value);
        while (!str_starts_with($hash, "0000")) {
            $nonce++;
            $hash = sha1($nonce . "#" . $value);
        }
        echo $nonce . PHP_EOL;
        return $hash;
    }
}