<?php

namespace App;

abstract class Avatar
{

    public string $url;

    public function generate(string $hash = null): void {
        $client = $this->getClient();

        if($hash == null) {
            $this->url = $client->getRandomAvatarUrl();
        } else {
            $this->url = $client->getAvatarFromHash($hash);
        }
    }

    protected abstract function getClient(): AvatarClientInterface;

}