<?php

namespace Ruler\Test\Operator;

use Ruler\Operator;
use Ruler\Context;
use Ruler\Variable;
use PHPUnit\Framework\TestCase;

class EqualToTest extends TestCase
{
    public function testInterface()
    {
        $varA = new Variable('a', 1);
        $varB = new Variable('b', 2);

        $op = new Operator\EqualTo($varA, $varB);
        $this->assertInstanceOf('Ruler\Proposition', $op);
    }

    public function testConstructorAndEvaluation()
    {
        $varA    = new Variable('a', 1);
        $varB    = new Variable('b', 2);
        $context = new Context();

        $op = new Operator\EqualTo($varA, $varB);
        $this->assertFalse($op->evaluate($context));

        $context['a'] = 2;
        $this->assertTrue($op->evaluate($context));

        $context['a'] = 3;
        $context['b'] = function () {
            return 3;
        };
        $this->assertTrue($op->evaluate($context));
    }
}
