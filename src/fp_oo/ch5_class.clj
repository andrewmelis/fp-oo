(ns fp-oo.ch5-class)

(def method-from-message
  (fn [message class]
    (message (:__instance_methods__ class))))

(def apply-message-to
  (fn [class instance message args]
    (let [method (or (method-from-message message class)
                     message)]
      (apply method instance args))))

(def make
  (fn [class & args]
    (let [seeded {:__class_symbol__ (:__own_symbol__ class)}]
      (apply-message-to class seeded :add-instance-values args))))

(def class-from-instance
  (fn [instance]
    (eval (:__class_symbol__ instance))))

(def send-to
  (fn [instance message & args]
    (apply-message-to (class-from-instance instance)
                      instance
                      message 
                      args)))

(def Point
  {
   :__own_symbol__ 'Point   ;; or `Point? -- that's a backtick
   :__instance_methods__
   {
    :add-instance-values
    (fn [this x y]
      (assoc this :x x :y y))

    :class-name :__class_symbol__

    :class class-from-instance

    :shift
    (fn [this xinc yinc]
      (make Point (+ (:x this) xinc)
            (+ (:y this) yinc)))
    }
   })

(def Holder
  {
   :__own_symbol__ 'Holder
   :__instance_methods__
   {
    :add-instance-values (fn [this held] (assoc this :held held))
    }
   })
