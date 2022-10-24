<?php

namespace App\State;

use App\Neufplate;
use SplObjectStorage;
use SplObserver;
use SplSubject;

abstract class State implements SplSubject
{
    protected Neufplate $neufplate;
    private SplObjectStorage $observers;

    public function __construct(Neufplate $neufplate)
    {
        $this->observers = new SplObjectStorage();
        $this->neufplate = $neufplate;
    }

    public abstract function onTitling() : ?string;
    public abstract function onMakingCollision() : ?string;
    public abstract function onGenerating() : ?string;

    public function attach(SplObserver $observer): void
    {
        $this->observers->attach($observer);
    }

    public function detach(SplObserver $observer): void
    {
        $this->observers->detach($observer);
    }

    public function notify(): void
    {
        /** @var SplObserver $observer */
        foreach ($this->observers as $observer) {
            $observer->update($this);
        }
    }
}