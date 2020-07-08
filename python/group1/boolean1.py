def find_it(s):
    c = s in 'The Truck missed the car with roses and ran into an Elephant.'
    return c


def main():
    print(find_it('Car'))
    print(find_it('Truck'))
    print(find_it('55684'))
    print(find_it('Elephant'))
    print(find_it('Roses'))


if __name__ == '__main__':
    main()
