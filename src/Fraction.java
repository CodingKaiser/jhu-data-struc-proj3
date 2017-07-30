public class Fraction {

    int num;
    int den;

    public Fraction(int num, int den) {
        this.num = num;
        this.den = den;
    }

    public Fraction(int num) {
        this.num = num;
        this.den = 1;
    }

    public Fraction mult(Fraction x) {
        return new Fraction(this.num * x.num, this.den * x.den);
    }

    public Fraction div(Fraction x) {
        if (x.num < 0) {
            return new Fraction(this.num * x.den * -1, this.den * x.num * -1);
        } else {
            return new Fraction(this.num * x.den, this.den * x.num);
        }
    }

    public Fraction sub(Fraction x) {
        return new Fraction(this.num * x.den - x.num * this.den, this.den * x.den);
    }

    public Fraction add(Fraction x) {
        return new Fraction(this.num * x.den + x.num * this.den, this.den * x.den);
    }

    public boolean equalsZero() {
        return this.num == 0;
    }

    public int toInt() {
        return this.num / this.den;
    }

    @Override
    public String toString() {
        if (this.num == 0 || this.den == 1) {
            return this.num + "";
        } else {
            return this.num + "/" + this.den;
        }
    }
}
