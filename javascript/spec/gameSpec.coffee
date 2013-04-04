# calculatorSpec.coffee

describe 'Calculator', ->

	it 'can add two positive numbers', ->
		calculator = new Calculator()
		result = calculator.add 2, 3
		expect(result).toBe 5