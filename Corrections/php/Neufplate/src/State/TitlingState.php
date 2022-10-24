<?php

namespace App\State;

use App\CorporateBsClient;
use App\Event\EmailNotificationListener;
use App\Neufplate;

class TitlingState extends State
{


    public function __construct(Neufplate $neufplate)
    {
        parent::__construct($neufplate);
    }

    public function onTitling(): ?string
    {
        $title = CorporateBsClient::generateCorporateBs();
        $this->neufplate->nft->title = $title;
        $this->neufplate->changeState(new MakingCollisionState($this->neufplate));
        return $title;
    }

    public function onMakingCollision(): ?string
    {
        return null;
    }

    public function onGenerating(): ?string
    {
        return null;
    }
}