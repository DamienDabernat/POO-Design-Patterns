<?php

namespace App\AvatarProvider;

enum ProviderType: String {
    case ROBOTHASH = 'robohash';
    case DICEBEAR = 'dicebear';
}