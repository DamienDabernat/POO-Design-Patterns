<?php

namespace App\User;

class UserBuilder implements Builder
{

    private User $user;

    public function __construct()
    {
        $this->reset();
    }

    public function reset(): void
    {
        $this->user = new User();
    }

    public function addEmail(string $email): UserBuilder
    {
        $this->user->email = $email;
        return $this;
    }

    public function addPhone(string $phone): UserBuilder
    {
        $this->user->phone = $phone;
        return $this;
    }

    public function addAddress(string $address): UserBuilder
    {
        $this->user->address = $address;
        return $this;
    }

    public function addNames(string $firstname, string $lastname): UserBuilder
    {
        $this->user->firstname = $firstname;
        $this->user->lastname = $lastname;
        return $this;
    }

    public function build(): User
    {
        $this->validateUser();
        $result = $this->user;
        $this->reset();
        return $result;
    }

    private function validateUser(): void
    {
        if (empty($this->user->firstname) || empty($this->user->lastname)) {
            throw new \RuntimeException('User must have a firstname and lastname');
        }

        if(empty($this->user->email) && empty($this->user->phone)) {
            throw new \RuntimeException('User must have an email or a phone number');
        }
    }
}