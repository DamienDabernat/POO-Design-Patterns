<?php

namespace App;

class CorporateBsClient
{
    private static ?CorporateBsClient $_instance = null;

    private const apiUrl = "https://corporatebs-generator.sameerkumar.website";

    public function __construct()
    {
    }

    public static function getInstance(): CorporateBsClient {

        if(is_null(self::$_instance)) {
            self::$_instance = new CorporateBsClient();
        }

        return self::$_instance;
    }


    public static function generateCorporateBs(): string
    {
        return self::parseJson(self::makeRequest());
    }

    private static function makeRequest(): string {
        return file_get_contents(self::apiUrl);
    }

    private static function parseJson(string $json) {
        $decoded_json = json_decode($json, false);
        return $decoded_json->phrase;
    }


}