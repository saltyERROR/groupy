package type_manager;

public interface Term {}
class Lambda implements Term{}
class Variable implements Term{}
class Function implements Term{}
class Let implements Term{}

