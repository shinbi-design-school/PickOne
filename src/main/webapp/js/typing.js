const doObserve = (element) => {
    const targets = document.querySelectorAll('.typeWriter'); /* ターゲットの指定 */
    const options = {
        root: null,
        rootMargin: '0px',
        threshold: 0
    };
    const observer = new IntersectionObserver((items) => {
        items.forEach((item) => {
            if (item.isIntersecting) {

                const typeWriter = selector => {
                    const el = document.querySelector(selector);
                    const text = el.innerHTML;

                    (function _type(i = 0) {
                        if (i === text.length) return;
                        el.innerHTML = text.substring(0, i + 1) + '<span aria-hidden="true"></span>';
                        setTimeout(() => _type(i + 1), 90);
                    })();
                };

                typeWriter(".typeWriter");
            } else {
                item.target.classList.remove('typing'); /* 表示域から外れた時にターゲットから削除するclassの指定 */
            }
        });
    }, options);
    Array.from(targets).forEach((target) => {
        observer.observe(target);
    });
};
doObserve('.observe_target');