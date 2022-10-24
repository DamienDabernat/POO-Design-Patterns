<?php

namespace App\AvatarProvider\RobotHash;

use App\Avatar;
use App\AvatarClientInterface;

class RobotHashAvatar extends Avatar
{

    protected function getClient(): AvatarClientInterface
    {
        return RobotHashClient::getInstance();
    }
}