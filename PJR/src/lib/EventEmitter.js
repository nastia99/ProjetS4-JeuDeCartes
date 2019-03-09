/**
 * Accepts event listener subscriptions, and notify when an event is emitted
 * Inspired from https://nodejs.org/api/events.html
 * 
 * @constructor
 */
export default class EventEmitter {
  constructor() {
    /** @private */ this.listeners = {};
  }

  /**
   * Subscribe a callback function to the given event
   * 
   * @param {string} event Event name
   * @param {function} callback Function to be called when the event is emitted
   */
  on(event, callback) {
    if(typeof callback !== 'function')
      throw new TypeError('Callback must be a function');
    if(typeof this.listeners === 'object') {
      if(Array.isArray(this.listeners[event]) && !this.listeners[event].includes(callback))
        this.listeners[event].push(callback);
      else
        this.listeners[event] = [callback];
    }
    return this;
  }

  /**
   * Unsubscribe a callback function from the given event
   * 
   * @param {string} event Event name
   * @param {function} callback Function to unsubscribe
   */
  off(event, callback) {
    if(typeof this.listeners === 'object') {
      let eventCallbacks = this.listeners[event];
      if(Array.isArray(eventCallbacks) && eventCallbacks.includes(callback))
        eventCallbacks.splice(eventCallbacks.indexOf(callback), 1);
    }
  }

  /**
   * Call the subscribed callback functions of the given event
   * 
   * @param {string} event Event name
   * @param  {...any} args Arguments passed to the callback functions
   */
  emit(event, ...args) {
    if(typeof this.listeners === 'object' && Array.isArray(this.listeners[event])) {
      this.listeners[event]
        .map(callback => callback(...args));
    }
  }

  /**
   * When the given event is emitted on the current object,
   * it emits the same event on the given object
   * 
   * @param {Object} thisArg Object who will relay the event(s)
   * @param {string | string[]} event Event(s) to relay
   */
  relay(thisArg, event) {
    if(thisArg instanceof EventEmitter) {
      if(!Array.isArray(event))
        event = [event];
      event.map(ev => 
        this.on(ev, (...args) => thisArg.emit(ev, ...args)));
    }
    return this;
  }
}