<?php

namespace App\AvatarProvider;

enum SpriteType: String {
    case MALE = 'male';
    case HUMAN = 'human';
    case FEMALE = 'female';
    case IDENTICON = 'identicon';
    case INITIALS = 'initials';
    case BOTTTS = 'bottts';
    case AVATAAARS = 'avataaars';
    case JDENTICON = 'jdenticon';
    case GRIDY = 'gridy';
    case MICAH = 'micah';
    case DEFAULT = 'default';
}