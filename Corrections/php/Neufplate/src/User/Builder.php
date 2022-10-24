<?php

namespace App\User;

interface Builder
{
    public function addNames(string $firstname, string $lastname): Builder;
    public function addEmail(string $email): Builder;
    public function addPhone(string $phone): Builder;
    public function addAddress(string $address): Builder;
}